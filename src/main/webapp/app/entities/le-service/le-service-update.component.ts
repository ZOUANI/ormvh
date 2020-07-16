import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ILeService, LeService } from 'app/shared/model/le-service.model';
import { LeServiceService } from './le-service.service';

@Component({
  selector: 'jhi-le-service-update',
  templateUrl: './le-service-update.component.html',
})
export class LeServiceUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    title: [],
    description: [],
    createdAt: [],
    updatedAt: [],
    createdBy: [],
    updatedBy: [],
  });

  constructor(protected leServiceService: LeServiceService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ leService }) => {
      if (!leService.id) {
        const today = moment().startOf('day');
        leService.createdAt = today;
        leService.updatedAt = today;
      }

      this.updateForm(leService);
    });
  }

  updateForm(leService: ILeService): void {
    this.editForm.patchValue({
      id: leService.id,
      title: leService.title,
      description: leService.description,
      createdAt: leService.createdAt ? leService.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: leService.updatedAt ? leService.updatedAt.format(DATE_TIME_FORMAT) : null,
      createdBy: leService.createdBy,
      updatedBy: leService.updatedBy,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const leService = this.createFromForm();
    if (leService.id !== undefined) {
      this.subscribeToSaveResponse(this.leServiceService.update(leService));
    } else {
      this.subscribeToSaveResponse(this.leServiceService.create(leService));
    }
  }

  private createFromForm(): ILeService {
    return {
      ...new LeService(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      description: this.editForm.get(['description'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? moment(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILeService>>): void {
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
