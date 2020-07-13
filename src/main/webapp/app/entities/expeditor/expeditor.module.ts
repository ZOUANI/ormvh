import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { OrmvahSharedModule } from 'app/shared';
import {
  ExpeditorComponent,
  ExpeditorDetailComponent,
  ExpeditorUpdateComponent,
  ExpeditorDeletePopupComponent,
  ExpeditorDeleteDialogComponent,
  expeditorRoute,
  expeditorPopupRoute
} from './';

const ENTITY_STATES = [...expeditorRoute, ...expeditorPopupRoute];

@NgModule({
  imports: [OrmvahSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ExpeditorComponent,
    ExpeditorDetailComponent,
    ExpeditorUpdateComponent,
    ExpeditorDeleteDialogComponent,
    ExpeditorDeletePopupComponent
  ],
  entryComponents: [ExpeditorComponent, ExpeditorUpdateComponent, ExpeditorDeleteDialogComponent, ExpeditorDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OrmvahExpeditorModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
