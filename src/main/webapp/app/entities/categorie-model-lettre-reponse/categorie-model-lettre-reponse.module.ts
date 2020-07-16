import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OrmvahSharedModule } from 'app/shared/shared.module';
import { CategorieModelLettreReponseComponent } from './categorie-model-lettre-reponse.component';
import { CategorieModelLettreReponseDetailComponent } from './categorie-model-lettre-reponse-detail.component';
import { CategorieModelLettreReponseUpdateComponent } from './categorie-model-lettre-reponse-update.component';
import { CategorieModelLettreReponseDeleteDialogComponent } from './categorie-model-lettre-reponse-delete-dialog.component';
import { categorieModelLettreReponseRoute } from './categorie-model-lettre-reponse.route';

@NgModule({
  imports: [OrmvahSharedModule, RouterModule.forChild(categorieModelLettreReponseRoute)],
  declarations: [
    CategorieModelLettreReponseComponent,
    CategorieModelLettreReponseDetailComponent,
    CategorieModelLettreReponseUpdateComponent,
    CategorieModelLettreReponseDeleteDialogComponent,
  ],
  entryComponents: [CategorieModelLettreReponseDeleteDialogComponent],
})
export class OrmvahCategorieModelLettreReponseModule {}
