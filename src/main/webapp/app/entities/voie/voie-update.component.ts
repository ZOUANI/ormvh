import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IVoie, Voie } from 'app/shared/model/voie.model';
import { VoieService } from './voie.service';

@Component({
  selector: 'jhi-voie-update',
  templateUrl: './voie-update.component.html'
})
export class VoieUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    libelle: [null, [Validators.required]],
    createdAt: [],
    updatedAt: [],
    createdBy: [],
    updatedBy: []
  });

  constructor(protected voieService: VoieService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ voie }) => {
      this.updateForm(voie);
    });
  }

  updateForm(voie: IVoie) {
    this.editForm.patchValue({
      id: voie.id,
      libelle: voie.libelle,
      createdAt: voie.createdAt != null ? voie.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: voie.updatedAt != null ? voie.updatedAt.format(DATE_TIME_FORMAT) : null,
      createdBy: voie.createdBy,
      updatedBy: voie.updatedBy
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
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
      id: this.editForm.get(['id']).value,
      libelle: this.editForm.get(['libelle']).value,
      createdAt:
        this.editForm.get(['createdAt']).value != null ? moment(this.editForm.get(['createdAt']).value, DATE_TIME_FORMAT) : undefined,
      updatedAt:
        this.editForm.get(['updatedAt']).value != null ? moment(this.editForm.get(['updatedAt']).value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy']).value,
      updatedBy: this.editForm.get(['updatedBy']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVoie>>) {
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
