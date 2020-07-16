import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { INatureCourrier, NatureCourrier } from 'app/shared/model/nature-courrier.model';
import { NatureCourrierService } from './nature-courrier.service';

@Component({
  selector: 'jhi-nature-courrier-update',
  templateUrl: './nature-courrier-update.component.html',
})
export class NatureCourrierUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    libelle: [null, [Validators.required]],
    delai: [],
    relance: [],
    createdAt: [],
    updatedAt: [],
    createdBy: [],
    updatedBy: [],
  });

  constructor(protected natureCourrierService: NatureCourrierService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ natureCourrier }) => {
      if (!natureCourrier.id) {
        const today = moment().startOf('day');
        natureCourrier.createdAt = today;
        natureCourrier.updatedAt = today;
      }

      this.updateForm(natureCourrier);
    });
  }

  updateForm(natureCourrier: INatureCourrier): void {
    this.editForm.patchValue({
      id: natureCourrier.id,
      libelle: natureCourrier.libelle,
      delai: natureCourrier.delai,
      relance: natureCourrier.relance,
      createdAt: natureCourrier.createdAt ? natureCourrier.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: natureCourrier.updatedAt ? natureCourrier.updatedAt.format(DATE_TIME_FORMAT) : null,
      createdBy: natureCourrier.createdBy,
      updatedBy: natureCourrier.updatedBy,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const natureCourrier = this.createFromForm();
    if (natureCourrier.id !== undefined) {
      this.subscribeToSaveResponse(this.natureCourrierService.update(natureCourrier));
    } else {
      this.subscribeToSaveResponse(this.natureCourrierService.create(natureCourrier));
    }
  }

  private createFromForm(): INatureCourrier {
    return {
      ...new NatureCourrier(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      delai: this.editForm.get(['delai'])!.value,
      relance: this.editForm.get(['relance'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? moment(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INatureCourrier>>): void {
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
