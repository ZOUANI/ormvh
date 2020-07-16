import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IVoie, Voie } from 'app/shared/model/voie.model';
import { VoieService } from './voie.service';

@Component({
  selector: 'jhi-voie-update',
  templateUrl: './voie-update.component.html',
})
export class VoieUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    libelle: [null, [Validators.required]],
    createdAt: [],
    updatedAt: [],
    createdBy: [],
    updatedBy: [],
  });

  constructor(protected voieService: VoieService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ voie }) => {
      if (!voie.id) {
        const today = moment().startOf('day');
        voie.createdAt = today;
        voie.updatedAt = today;
      }

      this.updateForm(voie);
    });
  }

  updateForm(voie: IVoie): void {
    this.editForm.patchValue({
      id: voie.id,
      libelle: voie.libelle,
      createdAt: voie.createdAt ? voie.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: voie.updatedAt ? voie.updatedAt.format(DATE_TIME_FORMAT) : null,
      createdBy: voie.createdBy,
      updatedBy: voie.updatedBy,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const voie = this.createFromForm();
    if (voie.id !== undefined) {
      this.subscribeToSaveResponse(this.voieService.update(voie));
    } else {
      this.subscribeToSaveResponse(this.voieService.create(voie));
    }
  }

  private createFromForm(): IVoie {
    return {
      ...new Voie(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? moment(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVoie>>): void {
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
