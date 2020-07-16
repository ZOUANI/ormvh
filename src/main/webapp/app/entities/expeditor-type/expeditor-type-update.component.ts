import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IExpeditorType, ExpeditorType } from 'app/shared/model/expeditor-type.model';
import { ExpeditorTypeService } from './expeditor-type.service';

@Component({
  selector: 'jhi-expeditor-type-update',
  templateUrl: './expeditor-type-update.component.html',
})
export class ExpeditorTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    title: [],
    createdAt: [],
    updatedAt: [],
    createdBy: [],
    updatedBy: [],
  });

  constructor(protected expeditorTypeService: ExpeditorTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ expeditorType }) => {
      if (!expeditorType.id) {
        const today = moment().startOf('day');
        expeditorType.createdAt = today;
        expeditorType.updatedAt = today;
      }

      this.updateForm(expeditorType);
    });
  }

  updateForm(expeditorType: IExpeditorType): void {
    this.editForm.patchValue({
      id: expeditorType.id,
      title: expeditorType.title,
      createdAt: expeditorType.createdAt ? expeditorType.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: expeditorType.updatedAt ? expeditorType.updatedAt.format(DATE_TIME_FORMAT) : null,
      createdBy: expeditorType.createdBy,
      updatedBy: expeditorType.updatedBy,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const expeditorType = this.createFromForm();
    if (expeditorType.id !== undefined) {
      this.subscribeToSaveResponse(this.expeditorTypeService.update(expeditorType));
    } else {
      this.subscribeToSaveResponse(this.expeditorTypeService.create(expeditorType));
    }
  }

  private createFromForm(): IExpeditorType {
    return {
      ...new ExpeditorType(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? moment(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExpeditorType>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
