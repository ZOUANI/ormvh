import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ICourrierObject, CourrierObject } from 'app/shared/model/courrier-object.model';
import { CourrierObjectService } from './courrier-object.service';

@Component({
  selector: 'jhi-courrier-object-update',
  templateUrl: './courrier-object-update.component.html'
})
export class CourrierObjectUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    title: [],
    category: [],
    createdAt: [],
    updatedAt: [],
    createdBy: [],
    updatedBy: []
  });

  constructor(protected courrierObjectService: CourrierObjectService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ courrierObject }) => {
      this.updateForm(courrierObject);
    });
  }

  updateForm(courrierObject: ICourrierObject) {
    this.editForm.patchValue({
      id: courrierObject.id,
      title: courrierObject.title,
      category: courrierObject.category,
      createdAt: courrierObject.createdAt != null ? courrierObject.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: courrierObject.updatedAt != null ? courrierObject.updatedAt.format(DATE_TIME_FORMAT) : null,
      createdBy: courrierObject.createdBy,
      updatedBy: courrierObject.updatedBy
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const courrierObject = this.createFromForm();
    if (courrierObject.id !== undefined) {
      this.subscribeToSaveResponse(this.courrierObjectService.update(courrierObject));
    } else {
      this.subscribeToSaveResponse(this.courrierObjectService.create(courrierObject));
    }
  }

  private createFromForm(): ICourrierObject {
    return {
      ...new CourrierObject(),
      id: this.editForm.get(['id']).value,
      title: this.editForm.get(['title']).value,
      category: this.editForm.get(['category']).value,
      createdAt:
        this.editForm.get(['createdAt']).value != null ? moment(this.editForm.get(['createdAt']).value, DATE_TIME_FORMAT) : undefined,
      updatedAt:
        this.editForm.get(['updatedAt']).value != null ? moment(this.editForm.get(['updatedAt']).value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy']).value,
      updatedBy: this.editForm.get(['updatedBy']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICourrierObject>>) {
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
