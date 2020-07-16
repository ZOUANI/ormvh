import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICategorieModelLettreReponse, CategorieModelLettreReponse } from 'app/shared/model/categorie-model-lettre-reponse.model';
import { CategorieModelLettreReponseService } from './categorie-model-lettre-reponse.service';
import { CategorieModelLettreReponseComponent } from './categorie-model-lettre-reponse.component';
import { CategorieModelLettreReponseDetailComponent } from './categorie-model-lettre-reponse-detail.component';
import { CategorieModelLettreReponseUpdateComponent } from './categorie-model-lettre-reponse-update.component';

@Injectable({ providedIn: 'root' })
export class CategorieModelLettreReponseResolve implements Resolve<ICategorieModelLettreReponse> {
  constructor(private service: CategorieModelLettreReponseService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICategorieModelLettreReponse> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((categorieModelLettreReponse: HttpResponse<CategorieModelLettreReponse>) => {
          if (categorieModelLettreReponse.body) {
            return of(categorieModelLettreReponse.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CategorieModelLettreReponse());
  }
}

export const categorieModelLettreReponseRoute: Routes = [
  {
    path: '',
    component: CategorieModelLettreReponseComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.categorieModelLettreReponse.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CategorieModelLettreReponseDetailComponent,
    resolve: {
      categorieModelLettreReponse: CategorieModelLettreReponseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.categorieModelLettreReponse.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CategorieModelLettreReponseUpdateComponent,
    resolve: {
      categorieModelLettreReponse: CategorieModelLettreReponseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.categorieModelLettreReponse.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CategorieModelLettreReponseUpdateComponent,
    resolve: {
      categorieModelLettreReponse: CategorieModelLettreReponseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.categorieModelLettreReponse.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
