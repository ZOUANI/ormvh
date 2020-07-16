import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'courrier',
        loadChildren: () => import('./courrier/courrier.module').then(m => m.OrmvahCourrierModule),
      },
      {
        path: 'nature-courrier',
        loadChildren: () => import('./nature-courrier/nature-courrier.module').then(m => m.OrmvahNatureCourrierModule),
      },
      {
        path: 'voie',
        loadChildren: () => import('./voie/voie.module').then(m => m.OrmvahVoieModule),
      },
      {
        path: 'expeditor',
        loadChildren: () => import('./expeditor/expeditor.module').then(m => m.OrmvahExpeditorModule),
      },
      {
        path: 'expeditor-type',
        loadChildren: () => import('./expeditor-type/expeditor-type.module').then(m => m.OrmvahExpeditorTypeModule),
      },
      {
        path: 'subdivision',
        loadChildren: () => import('./subdivision/subdivision.module').then(m => m.OrmvahSubdivisionModule),
      },
      {
        path: 'le-service',
        loadChildren: () => import('./le-service/le-service.module').then(m => m.OrmvahLeServiceModule),
      },
      {
        path: 'employee',
        loadChildren: () => import('./employee/employee.module').then(m => m.OrmvahEmployeeModule),
      },
      {
        path: 'task',
        loadChildren: () => import('./task/task.module').then(m => m.OrmvahTaskModule),
      },
      {
        path: 'evaluation',
        loadChildren: () => import('./evaluation/evaluation.module').then(m => m.OrmvahEvaluationModule),
      },
      {
        path: 'courrier-object',
        loadChildren: () => import('./courrier-object/courrier-object.module').then(m => m.OrmvahCourrierObjectModule),
      },
      {
        path: 'model-lettre-reponse',
        loadChildren: () => import('./model-lettre-reponse/model-lettre-reponse.module').then(m => m.OrmvahModelLettreReponseModule),
      },
      {
        path: 'categorie-model-lettre-reponse',
        loadChildren: () =>
          import('./categorie-model-lettre-reponse/categorie-model-lettre-reponse.module').then(
            m => m.OrmvahCategorieModelLettreReponseModule
          ),
      },
      {
        path: 'bordereau',
        loadChildren: () => import('./bordereau/bordereau.module').then(m => m.OrmvahBordereauModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class OrmvahEntityModule {}
