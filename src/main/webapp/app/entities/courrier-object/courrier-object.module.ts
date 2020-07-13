import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { OrmvahSharedModule } from 'app/shared';
import {
  CourrierObjectComponent,
  CourrierObjectDetailComponent,
  CourrierObjectUpdateComponent,
  CourrierObjectDeletePopupComponent,
  CourrierObjectDeleteDialogComponent,
  courrierObjectRoute,
  courrierObjectPopupRoute
} from './';

const ENTITY_STATES = [...courrierObjectRoute, ...courrierObjectPopupRoute];

@NgModule({
  imports: [OrmvahSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CourrierObjectComponent,
    CourrierObjectDetailComponent,
    CourrierObjectUpdateComponent,
    CourrierObjectDeleteDialogComponent,
    CourrierObjectDeletePopupComponent
  ],
  entryComponents: [
    CourrierObjectComponent,
    CourrierObjectUpdateComponent,
    CourrierObjectDeleteDialogComponent,
    CourrierObjectDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OrmvahCourrierObjectModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
