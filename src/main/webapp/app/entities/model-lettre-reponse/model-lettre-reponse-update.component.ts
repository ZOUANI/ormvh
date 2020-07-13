import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IModelLettreReponse, ModelLettreReponse } from 'app/shared/model/model-lettre-reponse.model';
import { ModelLettreReponseService } from './model-lettre-reponse.service';
import { ICategorieModelLettreReponse } from 'app/shared/model/categorie-model-lettre-reponse.model';
import { CategorieModelLettreReponseService } from 'app/entities/categorie-model-lettre-reponse';

@Component({
  selector: 'jhi-model-lettre-reponse-update',
  templateUrl: './model-lettre-reponse-update.component.html'
})
export class ModelLettreReponseUpdateComponent implements OnInit {
  isSaving: boolean;

  categoriemodellettrereponses: ICategorieModelLettreReponse[];

  editForm = this.fb.group({
    id: [],
    libelle: [null, [Validators.required]],
    code: [null, [Validators.required]],
    chemin: [null, [Validators.required]],
    categorieModelLettreReponse: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected modelLettreReponseService: ModelLettreReponseService,
    protected categorieModelLettreReponseService: CategorieModelLettreReponseService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ modelLettreReponse }) => {
      this.updateForm(modelLettreReponse);
    });
    this.categorieModelLettreReponseService
      .query({ filter: 'modellettrereponse-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<ICategorieModelLettreReponse[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICategorieModelLettreReponse[]>) => response.body)
      )
      .subscribe(
        (res: ICategorieModelLettreReponse[]) => {
          if (!this.editForm.get('categorieModelLettreReponse').value || !this.editForm.get('categorieModelLettreReponse').value.id) {
            this.categoriemodellettrereponses = res;
          } else {
            this.categorieModelLettreReponseService
              .find(this.editForm.get('categorieModelLettreReponse').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<ICategorieModelLettreReponse>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<ICategorieModelLettreReponse>) => subResponse.body)
              )
              .subscribe(
                (subRes: ICategorieModelLettreReponse) => (this.categoriemodellettrereponses = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(modelLettreReponse: IModelLettreReponse) {
    this.editForm.patchValue({
      id: modelLettreReponse.id,
      libelle: modelLettreReponse.libelle,
      code: modelLettreReponse.code,
      chemin: modelLettreReponse.chemin,
      categorieModelLettreReponse: modelLettreReponse.categorieModelLettreReponse
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
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
      id: this.editForm.get(['id']).value,
      libelle: this.editForm.get(['libelle']).value,
      code: this.editForm.get(['code']).value,
      chemin: this.editForm.get(['chemin']).value,
      categorieModelLettreReponse: this.editForm.get(['categorieModelLettreReponse']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IModelLettreReponse>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackCategorieModelLettreReponseById(index: number, item: ICategorieModelLettreReponse) {
    return item.id;
  }
}
