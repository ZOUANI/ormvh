import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { OrmvahSharedModule } from 'app/shared';
import {
  ModelLettreReponseComponent,
  ModelLettreReponseDetailComponent,
  ModelLettreReponseUpdateComponent,
  ModelLettreReponseDeletePopupComponent,
  ModelLettreReponseDeleteDialogComponent,
  modelLettreReponseRoute,
  modelLettreReponsePopupRoute
} from './';

const ENTITY_STATES = [...modelLettreReponseRoute, ...modelLettreReponsePopupRoute];

@NgModule({
  imports: [OrmvahSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ModelLettreReponseComponent,
    ModelLettreReponseDetailComponent,
    ModelLettreReponseUpdateComponent,
    ModelLettreReponseDeleteDialogComponent,
    ModelLettreReponseDeletePopupComponent
  ],
  entryComponents: [
    ModelLettreReponseComponent,
    ModelLettreReponseUpdateComponent,
    ModelLettreReponseDeleteDialogComponent,
    ModelLettreReponseDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OrmvahModelLettreReponseModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
