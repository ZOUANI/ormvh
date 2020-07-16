import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILeService, LeService } from 'app/shared/model/le-service.model';
import { LeServiceService } from './le-service.service';
import { LeServiceComponent } from './le-service.component';
import { LeServiceDetailComponent } from './le-service-detail.component';
import { LeServiceUpdateComponent } from './le-service-update.component';

@Injectable({ providedIn: 'root' })
export class LeServiceResolve implements Resolve<ILeService> {
  constructor(private service: LeServiceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILeService> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((leService: HttpResponse<LeService>) => {
          if (leService.body) {
            return of(leService.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LeService());
  }
}

export const leServiceRoute: Routes = [
  {
    path: '',
    component: LeServiceComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'ormvahApp.leService.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LeServiceDetailComponent,
    resolve: {
      leService: LeServiceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.leService.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LeServiceUpdateComponent,
    resolve: {
      leService: LeServiceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.leService.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LeServiceUpdateComponent,
    resolve: {
      leService: LeServiceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.leService.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
