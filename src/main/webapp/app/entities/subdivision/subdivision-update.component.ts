import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ISubdivision, Subdivision } from 'app/shared/model/subdivision.model';
import { SubdivisionService } from './subdivision.service';

@Component({
  selector: 'jhi-subdivision-update',
  templateUrl: './subdivision-update.component.html'
})
export class SubdivisionUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    title: [],
    createdAt: [],
    updatedAt: [],
    createdBy: [],
    updatedBy: []
  });

  constructor(protected subdivisionService: SubdivisionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ subdivision }) => {
      this.updateForm(subdivision);
    });
  }

  updateForm(subdivision: ISubdivision) {
    this.editForm.patchValue({
      id: subdivision.id,
      title: subdivision.title,
      createdAt: subdivision.createdAt != null ? subdivision.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: subdivision.updatedAt != null ? subdivision.updatedAt.format(DATE_TIME_FORMAT) : null,
      createdBy: subdivision.createdBy,
      updatedBy: subdivision.updatedBy
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const subdivision = this.createFromForm();
    if (subdivision.id !== undefined) {
      this.subscribeToSaveResponse(this.subdivisionService.update(subdivision));
    } else {
      this.subscribeToSaveResponse(this.subdivisionService.create(subdivision));
    }
  }

  private createFromForm(): ISubdivision {
    return {
      ...new Subdivision(),
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubdivision>>) {
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
