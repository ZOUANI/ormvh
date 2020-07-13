import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { OrmvahSharedModule } from 'app/shared';
import {
  CourrierComponent,
  CourrierDetailComponent,
  CourrierUpdateComponent,
  CourrierDeletePopupComponent,
  CourrierDeleteDialogComponent,
  courrierRoute,
  courrierPopupRoute
} from './';

const ENTITY_STATES = [...courrierRoute, ...courrierPopupRoute];

@NgModule({
  imports: [OrmvahSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CourrierComponent,
    CourrierDetailComponent,
    CourrierUpdateComponent,
    CourrierDeleteDialogComponent,
    CourrierDeletePopupComponent
  ],
  entryComponents: [CourrierComponent, CourrierUpdateComponent, CourrierDeleteDialogComponent, CourrierDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OrmvahCourrierModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
