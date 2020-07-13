import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { OrmvahSharedModule } from 'app/shared';
import {
  BordereauComponent,
  BordereauDetailComponent,
  BordereauUpdateComponent,
  BordereauDeletePopupComponent,
  BordereauDeleteDialogComponent,
  bordereauRoute,
  bordereauPopupRoute
} from './';

const ENTITY_STATES = [...bordereauRoute, ...bordereauPopupRoute];

@NgModule({
  imports: [OrmvahSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    BordereauComponent,
    BordereauDetailComponent,
    BordereauUpdateComponent,
    BordereauDeleteDialogComponent,
    BordereauDeletePopupComponent
  ],
  entryComponents: [BordereauComponent, BordereauUpdateComponent, BordereauDeleteDialogComponent, BordereauDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OrmvahBordereauModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
