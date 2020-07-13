import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { OrmvahSharedModule } from 'app/shared';
import {
  ExpeditorTypeComponent,
  ExpeditorTypeDetailComponent,
  ExpeditorTypeUpdateComponent,
  ExpeditorTypeDeletePopupComponent,
  ExpeditorTypeDeleteDialogComponent,
  expeditorTypeRoute,
  expeditorTypePopupRoute
} from './';

const ENTITY_STATES = [...expeditorTypeRoute, ...expeditorTypePopupRoute];

@NgModule({
  imports: [OrmvahSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ExpeditorTypeComponent,
    ExpeditorTypeDetailComponent,
    ExpeditorTypeUpdateComponent,
    ExpeditorTypeDeleteDialogComponent,
    ExpeditorTypeDeletePopupComponent
  ],
  entryComponents: [
    ExpeditorTypeComponent,
    ExpeditorTypeUpdateComponent,
    ExpeditorTypeDeleteDialogComponent,
    ExpeditorTypeDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OrmvahExpeditorTypeModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
