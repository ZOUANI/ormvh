import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CategorieModelLettreReponse } from 'app/shared/model/categorie-model-lettre-reponse.model';
import { CategorieModelLettreReponseService } from './categorie-model-lettre-reponse.service';
import { CategorieModelLettreReponseComponent } from './categorie-model-lettre-reponse.component';
import { CategorieModelLettreReponseDetailComponent } from './categorie-model-lettre-reponse-detail.component';
import { CategorieModelLettreReponseUpdateComponent } from './categorie-model-lettre-reponse-update.component';
import { CategorieModelLettreReponseDeletePopupComponent } from './categorie-model-lettre-reponse-delete-dialog.component';
import { ICategorieModelLettreReponse } from 'app/shared/model/categorie-model-lettre-reponse.model';

@Injectable({ providedIn: 'root' })
export class CategorieModelLettreReponseResolve implements Resolve<ICategorieModelLettreReponse> {
  constructor(private service: CategorieModelLettreReponseService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICategorieModelLettreReponse> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CategorieModelLettreReponse>) => response.ok),
        map((categorieModelLettreReponse: HttpResponse<CategorieModelLettreReponse>) => categorieModelLettreReponse.body)
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
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.categorieModelLettreReponse.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CategorieModelLettreReponseDetailComponent,
    resolve: {
      categorieModelLettreReponse: CategorieModelLettreReponseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.categorieModelLettreReponse.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CategorieModelLettreReponseUpdateComponent,
    resolve: {
      categorieModelLettreReponse: CategorieModelLettreReponseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.categorieModelLettreReponse.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CategorieModelLettreReponseUpdateComponent,
    resolve: {
      categorieModelLettreReponse: CategorieModelLettreReponseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.categorieModelLettreReponse.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const categorieModelLettreReponsePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CategorieModelLettreReponseDeletePopupComponent,
    resolve: {
      categorieModelLettreReponse: CategorieModelLettreReponseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.categorieModelLettreReponse.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
