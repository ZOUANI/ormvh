import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ISubdivision, Subdivision } from 'app/shared/model/subdivision.model';
import { SubdivisionService } from './subdivision.service';

@Component({
  selector: 'jhi-subdivision-update',
  templateUrl: './subdivision-update.component.html',
})
export class SubdivisionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    title: [],
    createdAt: [],
    updatedAt: [],
    createdBy: [],
    updatedBy: [],
  });

  constructor(protected subdivisionService: SubdivisionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subdivision }) => {
      if (!subdivision.id) {
        const today = moment().startOf('day');
        subdivision.createdAt = today;
        subdivision.updatedAt = today;
      }

      this.updateForm(subdivision);
    });
  }

  updateForm(subdivision: ISubdivision): void {
    this.editForm.patchValue({
      id: subdivision.id,
      title: subdivision.title,
      createdAt: subdivision.createdAt ? subdivision.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: subdivision.updatedAt ? subdivision.updatedAt.format(DATE_TIME_FORMAT) : null,
      createdBy: subdivision.createdBy,
      updatedBy: subdivision.updatedBy,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
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
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? moment(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubdivision>>): void {
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
