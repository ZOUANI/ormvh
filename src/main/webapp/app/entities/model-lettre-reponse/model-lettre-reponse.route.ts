import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ModelLettreReponse } from 'app/shared/model/model-lettre-reponse.model';
import { ModelLettreReponseService } from './model-lettre-reponse.service';
import { ModelLettreReponseComponent } from './model-lettre-reponse.component';
import { ModelLettreReponseDetailComponent } from './model-lettre-reponse-detail.component';
import { ModelLettreReponseUpdateComponent } from './model-lettre-reponse-update.component';
import { ModelLettreReponseDeletePopupComponent } from './model-lettre-reponse-delete-dialog.component';
import { IModelLettreReponse } from 'app/shared/model/model-lettre-reponse.model';

@Injectable({ providedIn: 'root' })
export class ModelLettreReponseResolve implements Resolve<IModelLettreReponse> {
  constructor(private service: ModelLettreReponseService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IModelLettreReponse> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ModelLettreReponse>) => response.ok),
        map((modelLettreReponse: HttpResponse<ModelLettreReponse>) => modelLettreReponse.body)
      );
    }
    return of(new ModelLettreReponse());
  }
}

export const modelLettreReponseRoute: Routes = [
  {
    path: '',
    component: ModelLettreReponseComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.modelLettreReponse.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ModelLettreReponseDetailComponent,
    resolve: {
      modelLettreReponse: ModelLettreReponseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.modelLettreReponse.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ModelLettreReponseUpdateComponent,
    resolve: {
      modelLettreReponse: ModelLettreReponseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.modelLettreReponse.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ModelLettreReponseUpdateComponent,
    resolve: {
      modelLettreReponse: ModelLettreReponseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.modelLettreReponse.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const modelLettreReponsePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ModelLettreReponseDeletePopupComponent,
    resolve: {
      modelLettreReponse: ModelLettreReponseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.modelLettreReponse.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
