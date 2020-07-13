import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { OrmvahSharedModule } from 'app/shared';
import {
  EvaluationComponent,
  EvaluationDetailComponent,
  EvaluationUpdateComponent,
  EvaluationDeletePopupComponent,
  EvaluationDeleteDialogComponent,
  evaluationRoute,
  evaluationPopupRoute
} from './';

const ENTITY_STATES = [...evaluationRoute, ...evaluationPopupRoute];

@NgModule({
  imports: [OrmvahSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    EvaluationComponent,
    EvaluationDetailComponent,
    EvaluationUpdateComponent,
    EvaluationDeleteDialogComponent,
    EvaluationDeletePopupComponent
  ],
  entryComponents: [EvaluationComponent, EvaluationUpdateComponent, EvaluationDeleteDialogComponent, EvaluationDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OrmvahEvaluationModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
