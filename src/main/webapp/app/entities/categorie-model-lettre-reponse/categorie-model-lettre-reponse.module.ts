import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { OrmvahSharedModule } from 'app/shared';
import {
  CategorieModelLettreReponseComponent,
  CategorieModelLettreReponseDetailComponent,
  CategorieModelLettreReponseUpdateComponent,
  CategorieModelLettreReponseDeletePopupComponent,
  CategorieModelLettreReponseDeleteDialogComponent,
  categorieModelLettreReponseRoute,
  categorieModelLettreReponsePopupRoute
} from './';

const ENTITY_STATES = [...categorieModelLettreReponseRoute, ...categorieModelLettreReponsePopupRoute];

@NgModule({
  imports: [OrmvahSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CategorieModelLettreReponseComponent,
    CategorieModelLettreReponseDetailComponent,
    CategorieModelLettreReponseUpdateComponent,
    CategorieModelLettreReponseDeleteDialogComponent,
    CategorieModelLettreReponseDeletePopupComponent
  ],
  entryComponents: [
    CategorieModelLettreReponseComponent,
    CategorieModelLettreReponseUpdateComponent,
    CategorieModelLettreReponseDeleteDialogComponent,
    CategorieModelLettreReponseDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OrmvahCategorieModelLettreReponseModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
