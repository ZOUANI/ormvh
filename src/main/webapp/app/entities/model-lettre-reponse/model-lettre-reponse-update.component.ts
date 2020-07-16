import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IModelLettreReponse, ModelLettreReponse } from 'app/shared/model/model-lettre-reponse.model';
import { ModelLettreReponseService } from './model-lettre-reponse.service';
import { ICategorieModelLettreReponse } from 'app/shared/model/categorie-model-lettre-reponse.model';
import { CategorieModelLettreReponseService } from 'app/entities/categorie-model-lettre-reponse/categorie-model-lettre-reponse.service';

@Component({
  selector: 'jhi-model-lettre-reponse-update',
  templateUrl: './model-lettre-reponse-update.component.html',
})
export class ModelLettreReponseUpdateComponent implements OnInit {
  isSaving = false;
  categoriemodellettrereponses: ICategorieModelLettreReponse[] = [];

  editForm = this.fb.group({
    id: [],
    libelle: [null, [Validators.required]],
    code: [null, [Validators.required]],
    chemin: [null, [Validators.required]],
    categorieModelLettreReponse: [],
  });

  constructor(
    protected modelLettreReponseService: ModelLettreReponseService,
    protected categorieModelLettreReponseService: CategorieModelLettreReponseService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ modelLettreReponse }) => {
      this.updateForm(modelLettreReponse);

      this.categorieModelLettreReponseService
        .query({ filter: 'modellettrereponse-is-null' })
        .pipe(
          map((res: HttpResponse<ICategorieModelLettreReponse[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICategorieModelLettreReponse[]) => {
          if (!modelLettreReponse.categorieModelLettreReponse || !modelLettreReponse.categorieModelLettreReponse.id) {
            this.categoriemodellettrereponses = resBody;
          } else {
            this.categorieModelLettreReponseService
              .find(modelLettreReponse.categorieModelLettreReponse.id)
              .pipe(
                map((subRes: HttpResponse<ICategorieModelLettreReponse>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICategorieModelLettreReponse[]) => (this.categoriemodellettrereponses = concatRes));
          }
        });
    });
  }

  updateForm(modelLettreReponse: IModelLettreReponse): void {
    this.editForm.patchValue({
      id: modelLettreReponse.id,
      libelle: modelLettreReponse.libelle,
      code: modelLettreReponse.code,
      chemin: modelLettreReponse.chemin,
      categorieModelLettreReponse: modelLettreReponse.categorieModelLettreReponse,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const modelLettreReponse = this.createFromForm();
    if (modelLettreReponse.id !== undefined) {
      this.subscribeToSaveResponse(this.modelLettreReponseService.update(modelLettreReponse));
    } else {
      this.subscribeToSaveResponse(this.modelLettreReponseService.create(modelLettreReponse));
    }
  }

  private createFromForm(): IModelLettreReponse {
    return {
      ...new ModelLettreReponse(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      code: this.editForm.get(['code'])!.value,
      chemin: this.editForm.get(['chemin'])!.value,
      categorieModelLettreReponse: this.editForm.get(['categorieModelLettreReponse'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IModelLettreReponse>>): void {
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

  trackById(index: number, item: ICategorieModelLettreReponse): any {
    return item.id;
  }
}
