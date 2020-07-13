import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'courrier',
        loadChildren: './courrier/courrier.module#OrmvahCourrierModule'
      },
      {
        path: 'nature-courrier',
        loadChildren: './nature-courrier/nature-courrier.module#OrmvahNatureCourrierModule'
      },
      {
        path: 'voie',
        loadChildren: './voie/voie.module#OrmvahVoieModule'
      },
      {
        path: 'expeditor',
        loadChildren: './expeditor/expeditor.module#OrmvahExpeditorModule'
      },
      {
        path: 'expeditor-type',
        loadChildren: './expeditor-type/expeditor-type.module#OrmvahExpeditorTypeModule'
      },
      {
        path: 'subdivision',
        loadChildren: './subdivision/subdivision.module#OrmvahSubdivisionModule'
      },
      {
        path: 'le-service',
        loadChildren: './le-service/le-service.module#OrmvahLeServiceModule'
      },
      {
        path: 'employee',
        loadChildren: './employee/employee.module#OrmvahEmployeeModule'
      },
      {
        path: 'task',
        loadChildren: './task/task.module#OrmvahTaskModule'
      },
      {
        path: 'evaluation',
        loadChildren: './evaluation/evaluation.module#OrmvahEvaluationModule'
      },
      {
        path: 'courrier-object',
        loadChildren: './courrier-object/courrier-object.module#OrmvahCourrierObjectModule'
      },
      {
        path: 'model-lettre-reponse',
        loadChildren: './model-lettre-reponse/model-lettre-reponse.module#OrmvahModelLettreReponseModule'
      },
      {
        path: 'categorie-model-lettre-reponse',
        loadChildren: './categorie-model-lettre-reponse/categorie-model-lettre-reponse.module#OrmvahCategorieModelLettreReponseModule'
      },
      {
        path: 'bordereau',
        loadChildren: './bordereau/bordereau.module#OrmvahBordereauModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OrmvahEntityModule {}
