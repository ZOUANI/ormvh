import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { OrmvahSharedModule } from 'app/shared';
import {
  VoieComponent,
  VoieDetailComponent,
  VoieUpdateComponent,
  VoieDeletePopupComponent,
  VoieDeleteDialogComponent,
  voieRoute,
  voiePopupRoute
} from './';

const ENTITY_STATES = [...voieRoute, ...voiePopupRoute];

@NgModule({
  imports: [OrmvahSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [VoieComponent, VoieDetailComponent, VoieUpdateComponent, VoieDeleteDialogComponent, VoieDeletePopupComponent],
  entryComponents: [VoieComponent, VoieUpdateComponent, VoieDeleteDialogComponent, VoieDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OrmvahVoieModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
