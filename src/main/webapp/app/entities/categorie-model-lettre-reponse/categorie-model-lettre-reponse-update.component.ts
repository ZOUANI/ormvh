import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ICategorieModelLettreReponse, CategorieModelLettreReponse } from 'app/shared/model/categorie-model-lettre-reponse.model';
import { CategorieModelLettreReponseService } from './categorie-model-lettre-reponse.service';

@Component({
  selector: 'jhi-categorie-model-lettre-reponse-update',
  templateUrl: './categorie-model-lettre-reponse-update.component.html'
})
export class CategorieModelLettreReponseUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    libelle: [null, [Validators.required]]
  });

  constructor(
    protected categorieModelLettreReponseService: CategorieModelLettreReponseService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ categorieModelLettreReponse }) => {
      this.updateForm(categorieModelLettreReponse);
    });
  }

  updateForm(categorieModelLettreReponse: ICategorieModelLettreReponse) {
    this.editForm.patchValue({
      id: categorieModelLettreReponse.id,
      libelle: categorieModelLettreReponse.libelle
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
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
      id: this.editForm.get(['id']).value,
      libelle: this.editForm.get(['libelle']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategorieModelLettreReponse>>) {
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
