import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ExpeditorType } from 'app/shared/model/expeditor-type.model';
import { ExpeditorTypeService } from './expeditor-type.service';
import { ExpeditorTypeComponent } from './expeditor-type.component';
import { ExpeditorTypeDetailComponent } from './expeditor-type-detail.component';
import { ExpeditorTypeUpdateComponent } from './expeditor-type-update.component';
import { ExpeditorTypeDeletePopupComponent } from './expeditor-type-delete-dialog.component';
import { IExpeditorType } from 'app/shared/model/expeditor-type.model';

@Injectable({ providedIn: 'root' })
export class ExpeditorTypeResolve implements Resolve<IExpeditorType> {
  constructor(private service: ExpeditorTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IExpeditorType> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ExpeditorType>) => response.ok),
        map((expeditorType: HttpResponse<ExpeditorType>) => expeditorType.body)
      );
    }
    return of(new ExpeditorType());
  }
}

export const expeditorTypeRoute: Routes = [
  {
    path: '',
    component: ExpeditorTypeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.expeditorType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ExpeditorTypeDetailComponent,
    resolve: {
      expeditorType: ExpeditorTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.expeditorType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ExpeditorTypeUpdateComponent,
    resolve: {
      expeditorType: ExpeditorTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.expeditorType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ExpeditorTypeUpdateComponent,
    resolve: {
      expeditorType: ExpeditorTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.expeditorType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const expeditorTypePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ExpeditorTypeDeletePopupComponent,
    resolve: {
      expeditorType: ExpeditorTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.expeditorType.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
