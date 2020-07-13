import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LeService } from 'app/shared/model/le-service.model';
import { LeServiceService } from './le-service.service';
import { LeServiceComponent } from './le-service.component';
import { LeServiceDetailComponent } from './le-service-detail.component';
import { LeServiceUpdateComponent } from './le-service-update.component';
import { LeServiceDeletePopupComponent } from './le-service-delete-dialog.component';
import { ILeService } from 'app/shared/model/le-service.model';

@Injectable({ providedIn: 'root' })
export class LeServiceResolve implements Resolve<ILeService> {
  constructor(private service: LeServiceService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILeService> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<LeService>) => response.ok),
        map((leService: HttpResponse<LeService>) => leService.body)
      );
    }
    return of(new LeService());
  }
}

export const leServiceRoute: Routes = [
  {
    path: '',
    component: LeServiceComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ormvahApp.leService.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LeServiceDetailComponent,
    resolve: {
      leService: LeServiceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.leService.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LeServiceUpdateComponent,
    resolve: {
      leService: LeServiceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.leService.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LeServiceUpdateComponent,
    resolve: {
      leService: LeServiceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.leService.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const leServicePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: LeServiceDeletePopupComponent,
    resolve: {
      leService: LeServiceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.leService.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
