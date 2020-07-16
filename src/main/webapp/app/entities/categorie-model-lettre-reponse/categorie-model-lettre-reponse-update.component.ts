import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICategorieModelLettreReponse, CategorieModelLettreReponse } from 'app/shared/model/categorie-model-lettre-reponse.model';
import { CategorieModelLettreReponseService } from './categorie-model-lettre-reponse.service';

@Component({
  selector: 'jhi-categorie-model-lettre-reponse-update',
  templateUrl: './categorie-model-lettre-reponse-update.component.html',
})
export class CategorieModelLettreReponseUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    libelle: [null, [Validators.required]],
  });

  constructor(
    protected categorieModelLettreReponseService: CategorieModelLettreReponseService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categorieModelLettreReponse }) => {
      this.updateForm(categorieModelLettreReponse);
    });
  }

  updateForm(categorieModelLettreReponse: ICategorieModelLettreReponse): void {
    this.editForm.patchValue({
      id: categorieModelLettreReponse.id,
      libelle: categorieModelLettreReponse.libelle,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const categorieModelLettreReponse = this.createFromForm();
    if (categorieModelLettreReponse.id !== undefined) {
      this.subscribeToSaveResponse(this.categorieModelLettreReponseService.update(categorieModelLettreReponse));
    } else {
      this.subscribeToSaveResponse(this.categorieModelLettreReponseService.create(categorieModelLettreReponse));
    }
  }

  private createFromForm(): ICategorieModelLettreReponse {
    return {
      ...new CategorieModelLettreReponse(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategorieModelLettreReponse>>): void {
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
