import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IModelLettreReponse, ModelLettreReponse } from 'app/shared/model/model-lettre-reponse.model';
import { ModelLettreReponseService } from './model-lettre-reponse.service';
import { ModelLettreReponseComponent } from './model-lettre-reponse.component';
import { ModelLettreReponseDetailComponent } from './model-lettre-reponse-detail.component';
import { ModelLettreReponseUpdateComponent } from './model-lettre-reponse-update.component';

@Injectable({ providedIn: 'root' })
export class ModelLettreReponseResolve implements Resolve<IModelLettreReponse> {
  constructor(private service: ModelLettreReponseService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IModelLettreReponse> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((modelLettreReponse: HttpResponse<ModelLettreReponse>) => {
          if (modelLettreReponse.body) {
            return of(modelLettreReponse.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
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
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.modelLettreReponse.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ModelLettreReponseDetailComponent,
    resolve: {
      modelLettreReponse: ModelLettreReponseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.modelLettreReponse.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ModelLettreReponseUpdateComponent,
    resolve: {
      modelLettreReponse: ModelLettreReponseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.modelLettreReponse.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ModelLettreReponseUpdateComponent,
    resolve: {
      modelLettreReponse: ModelLettreReponseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.modelLettreReponse.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
