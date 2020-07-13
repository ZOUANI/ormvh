import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Expeditor } from 'app/shared/model/expeditor.model';
import { ExpeditorService } from './expeditor.service';
import { ExpeditorComponent } from './expeditor.component';
import { ExpeditorDetailComponent } from './expeditor-detail.component';
import { ExpeditorUpdateComponent } from './expeditor-update.component';
import { ExpeditorDeletePopupComponent } from './expeditor-delete-dialog.component';
import { IExpeditor } from 'app/shared/model/expeditor.model';

@Injectable({ providedIn: 'root' })
export class ExpeditorResolve implements Resolve<IExpeditor> {
  constructor(private service: ExpeditorService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IExpeditor> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Expeditor>) => response.ok),
        map((expeditor: HttpResponse<Expeditor>) => expeditor.body)
      );
    }
    return of(new Expeditor());
  }
}

export const expeditorRoute: Routes = [
  {
    path: '',
    component: ExpeditorComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ormvahApp.expeditor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ExpeditorDetailComponent,
    resolve: {
      expeditor: ExpeditorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.expeditor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ExpeditorUpdateComponent,
    resolve: {
      expeditor: ExpeditorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.expeditor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ExpeditorUpdateComponent,
    resolve: {
      expeditor: ExpeditorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.expeditor.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const expeditorPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ExpeditorDeletePopupComponent,
    resolve: {
      expeditor: ExpeditorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.expeditor.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
