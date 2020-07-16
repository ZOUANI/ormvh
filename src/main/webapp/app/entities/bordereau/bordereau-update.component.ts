import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBordereau, Bordereau } from 'app/shared/model/bordereau.model';
import { BordereauService } from './bordereau.service';

@Component({
  selector: 'jhi-bordereau-update',
  templateUrl: './bordereau-update.component.html',
})
export class BordereauUpdateComponent implements OnInit {
  isSaving = false;
  dateBordereauxDp: any;

  editForm = this.fb.group({
    id: [],
    libelle: [null, [Validators.required]],
    description: [],
    nombrePieceJointe: [],
    dateBordereaux: [],
  });

  constructor(protected bordereauService: BordereauService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bordereau }) => {
      this.updateForm(bordereau);
    });
  }

  updateForm(bordereau: IBordereau): void {
    this.editForm.patchValue({
      id: bordereau.id,
      libelle: bordereau.libelle,
      description: bordereau.description,
      nombrePieceJointe: bordereau.nombrePieceJointe,
      dateBordereaux: bordereau.dateBordereaux,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
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
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      description: this.editForm.get(['description'])!.value,
      nombrePieceJointe: this.editForm.get(['nombrePieceJointe'])!.value,
      dateBordereaux: this.editForm.get(['dateBordereaux'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBordereau>>): void {
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
