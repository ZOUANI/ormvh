import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IExpeditor, Expeditor } from 'app/shared/model/expeditor.model';
import { ExpeditorService } from './expeditor.service';

@Component({
  selector: 'jhi-expeditor-update',
  templateUrl: './expeditor-update.component.html'
})
export class ExpeditorUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    title: [],
    description: [],
    nature: [],
    sexe: [],
    age: [],
    nationality: [],
    adress: [],
    createdAt: [],
    updatedAt: [],
    createdBy: [],
    updatedBy: []
  });

  constructor(protected expeditorService: ExpeditorService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ expeditor }) => {
      this.updateForm(expeditor);
    });
  }

  updateForm(expeditor: IExpeditor) {
    this.editForm.patchValue({
      id: expeditor.id,
      title: expeditor.title,
      description: expeditor.description,
      nature: expeditor.nature,
      sexe: expeditor.sexe,
      age: expeditor.age,
      nationality: expeditor.nationality,
      adress: expeditor.adress,
      createdAt: expeditor.createdAt != null ? expeditor.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: expeditor.updatedAt != null ? expeditor.updatedAt.format(DATE_TIME_FORMAT) : null,
      createdBy: expeditor.createdBy,
      updatedBy: expeditor.updatedBy
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const expeditor = this.createFromForm();
    if (expeditor.id !== undefined) {
      this.subscribeToSaveResponse(this.expeditorService.update(expeditor));
    } else {
      this.subscribeToSaveResponse(this.expeditorService.create(expeditor));
    }
  }

  private createFromForm(): IExpeditor {
    return {
      ...new Expeditor(),
      id: this.editForm.get(['id']).value,
      title: this.editForm.get(['title']).value,
      description: this.editForm.get(['description']).value,
      nature: this.editForm.get(['nature']).value,
      sexe: this.editForm.get(['sexe']).value,
      age: this.editForm.get(['age']).value,
      nationality: this.editForm.get(['nationality']).value,
      adress: this.editForm.get(['adress']).value,
      createdAt:
        this.editForm.get(['createdAt']).value != null ? moment(this.editForm.get(['createdAt']).value, DATE_TIME_FORMAT) : undefined,
      updatedAt:
        this.editForm.get(['updatedAt']).value != null ? moment(this.editForm.get(['updatedAt']).value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy']).value,
      updatedBy: this.editForm.get(['updatedBy']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExpeditor>>) {
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
