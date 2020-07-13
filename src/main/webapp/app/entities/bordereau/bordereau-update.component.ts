import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { IBordereau, Bordereau } from 'app/shared/model/bordereau.model';
import { BordereauService } from './bordereau.service';

@Component({
  selector: 'jhi-bordereau-update',
  templateUrl: './bordereau-update.component.html'
})
export class BordereauUpdateComponent implements OnInit {
  isSaving: boolean;
  dateBordereauxDp: any;

  editForm = this.fb.group({
    id: [],
    libelle: [null, [Validators.required]],
    description: [],
    nombrePieceJointe: [],
    dateBordereaux: []
  });

  constructor(protected bordereauService: BordereauService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ bordereau }) => {
      this.updateForm(bordereau);
    });
  }

  updateForm(bordereau: IBordereau) {
    this.editForm.patchValue({
      id: bordereau.id,
      libelle: bordereau.libelle,
      description: bordereau.description,
      nombrePieceJointe: bordereau.nombrePieceJointe,
      dateBordereaux: bordereau.dateBordereaux
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const bordereau = this.createFromForm();
    if (bordereau.id !== undefined) {
      this.subscribeToSaveResponse(this.bordereauService.update(bordereau));
    } else {
      this.subscribeToSaveResponse(this.bordereauService.create(bordereau));
    }
  }

  private createFromForm(): IBordereau {
    return {
      ...new Bordereau(),
      id: this.editForm.get(['id']).value,
      libelle: this.editForm.get(['libelle']).value,
      description: this.editForm.get(['description']).value,
      nombrePieceJointe: this.editForm.get(['nombrePieceJointe']).value,
      dateBordereaux: this.editForm.get(['dateBordereaux']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBordereau>>) {
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
