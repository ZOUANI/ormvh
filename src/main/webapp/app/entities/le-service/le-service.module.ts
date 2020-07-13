import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { OrmvahSharedModule } from 'app/shared';
import {
  LeServiceComponent,
  LeServiceDetailComponent,
  LeServiceUpdateComponent,
  LeServiceDeletePopupComponent,
  LeServiceDeleteDialogComponent,
  leServiceRoute,
  leServicePopupRoute
} from './';

const ENTITY_STATES = [...leServiceRoute, ...leServicePopupRoute];

@NgModule({
  imports: [OrmvahSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    LeServiceComponent,
    LeServiceDetailComponent,
    LeServiceUpdateComponent,
    LeServiceDeleteDialogComponent,
    LeServiceDeletePopupComponent
  ],
  entryComponents: [LeServiceComponent, LeServiceUpdateComponent, LeServiceDeleteDialogComponent, LeServiceDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OrmvahLeServiceModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
