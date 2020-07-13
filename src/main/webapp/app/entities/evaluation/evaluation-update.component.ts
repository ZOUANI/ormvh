import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IEvaluation, Evaluation } from 'app/shared/model/evaluation.model';
import { EvaluationService } from './evaluation.service';

@Component({
  selector: 'jhi-evaluation-update',
  templateUrl: './evaluation-update.component.html'
})
export class EvaluationUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    title: [],
    createdAt: [],
    updatedAt: [],
    createdBy: [],
    updatedBy: []
  });

  constructor(protected evaluationService: EvaluationService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ evaluation }) => {
      this.updateForm(evaluation);
    });
  }

  updateForm(evaluation: IEvaluation) {
    this.editForm.patchValue({
      id: evaluation.id,
      title: evaluation.title,
      createdAt: evaluation.createdAt != null ? evaluation.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: evaluation.updatedAt != null ? evaluation.updatedAt.format(DATE_TIME_FORMAT) : null,
      createdBy: evaluation.createdBy,
      updatedBy: evaluation.updatedBy
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const evaluation = this.createFromForm();
    if (evaluation.id !== undefined) {
      this.subscribeToSaveResponse(this.evaluationService.update(evaluation));
    } else {
      this.subscribeToSaveResponse(this.evaluationService.create(evaluation));
    }
  }

  private createFromForm(): IEvaluation {
    return {
      ...new Evaluation(),
      id: this.editForm.get(['id']).value,
      title: this.editForm.get(['title']).value,
      createdAt:
        this.editForm.get(['createdAt']).value != null ? moment(this.editForm.get(['createdAt']).value, DATE_TIME_FORMAT) : undefined,
      updatedAt:
        this.editForm.get(['updatedAt']).value != null ? moment(this.editForm.get(['updatedAt']).value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy']).value,
      updatedBy: this.editForm.get(['updatedBy']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEvaluation>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
