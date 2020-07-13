import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { INatureCourrier, NatureCourrier } from 'app/shared/model/nature-courrier.model';
import { NatureCourrierService } from './nature-courrier.service';

@Component({
  selector: 'jhi-nature-courrier-update',
  templateUrl: './nature-courrier-update.component.html'
})
export class NatureCourrierUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    libelle: [null, [Validators.required]],
    delai: [],
    relance: [],
    createdAt: [],
    updatedAt: [],
    createdBy: [],
    updatedBy: []
  });

  constructor(protected natureCourrierService: NatureCourrierService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ natureCourrier }) => {
      this.updateForm(natureCourrier);
    });
  }

  updateForm(natureCourrier: INatureCourrier) {
    this.editForm.patchValue({
      id: natureCourrier.id,
      libelle: natureCourrier.libelle,
      delai: natureCourrier.delai,
      relance: natureCourrier.relance,
      createdAt: natureCourrier.createdAt != null ? natureCourrier.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: natureCourrier.updatedAt != null ? natureCourrier.updatedAt.format(DATE_TIME_FORMAT) : null,
      createdBy: natureCourrier.createdBy,
      updatedBy: natureCourrier.updatedBy
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
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
      id: this.editForm.get(['id']).value,
      libelle: this.editForm.get(['libelle']).value,
      delai: this.editForm.get(['delai']).value,
      relance: this.editForm.get(['relance']).value,
      createdAt:
        this.editForm.get(['createdAt']).value != null ? moment(this.editForm.get(['createdAt']).value, DATE_TIME_FORMAT) : undefined,
      updatedAt:
        this.editForm.get(['updatedAt']).value != null ? moment(this.editForm.get(['updatedAt']).value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy']).value,
      updatedBy: this.editForm.get(['updatedBy']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INatureCourrier>>) {
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
