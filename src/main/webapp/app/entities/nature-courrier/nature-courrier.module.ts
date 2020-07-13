import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { OrmvahSharedModule } from 'app/shared';
import {
  NatureCourrierComponent,
  NatureCourrierDetailComponent,
  NatureCourrierUpdateComponent,
  NatureCourrierDeletePopupComponent,
  NatureCourrierDeleteDialogComponent,
  natureCourrierRoute,
  natureCourrierPopupRoute
} from './';

const ENTITY_STATES = [...natureCourrierRoute, ...natureCourrierPopupRoute];

@NgModule({
  imports: [OrmvahSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    NatureCourrierComponent,
    NatureCourrierDetailComponent,
    NatureCourrierUpdateComponent,
    NatureCourrierDeleteDialogComponent,
    NatureCourrierDeletePopupComponent
  ],
  entryComponents: [
    NatureCourrierComponent,
    NatureCourrierUpdateComponent,
    NatureCourrierDeleteDialogComponent,
    NatureCourrierDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OrmvahNatureCourrierModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
