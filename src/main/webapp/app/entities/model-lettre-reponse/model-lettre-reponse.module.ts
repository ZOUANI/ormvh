import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OrmvahSharedModule } from 'app/shared/shared.module';
import { ModelLettreReponseComponent } from './model-lettre-reponse.component';
import { ModelLettreReponseDetailComponent } from './model-lettre-reponse-detail.component';
import { ModelLettreReponseUpdateComponent } from './model-lettre-reponse-update.component';
import { ModelLettreReponseDeleteDialogComponent } from './model-lettre-reponse-delete-dialog.component';
import { modelLettreReponseRoute } from './model-lettre-reponse.route';

@NgModule({
  imports: [OrmvahSharedModule, RouterModule.forChild(modelLettreReponseRoute)],
  declarations: [
    ModelLettreReponseComponent,
    ModelLettreReponseDetailComponent,
    ModelLettreReponseUpdateComponent,
    ModelLettreReponseDeleteDialogComponent,
  ],
  entryComponents: [ModelLettreReponseDeleteDialogComponent],
})
export class OrmvahModelLettreReponseModule {}
