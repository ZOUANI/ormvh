import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICourrierObject, CourrierObject } from 'app/shared/model/courrier-object.model';
import { CourrierObjectService } from './courrier-object.service';
import { CourrierObjectComponent } from './courrier-object.component';
import { CourrierObjectDetailComponent } from './courrier-object-detail.component';
import { CourrierObjectUpdateComponent } from './courrier-object-update.component';

@Injectable({ providedIn: 'root' })
export class CourrierObjectResolve implements Resolve<ICourrierObject> {
  constructor(private service: CourrierObjectService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICourrierObject> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((courrierObject: HttpResponse<CourrierObject>) => {
          if (courrierObject.body) {
            return of(courrierObject.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CourrierObject());
  }
}

export const courrierObjectRoute: Routes = [
  {
    path: '',
    component: CourrierObjectComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.courrierObject.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CourrierObjectDetailComponent,
    resolve: {
      courrierObject: CourrierObjectResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.courrierObject.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CourrierObjectUpdateComponent,
    resolve: {
      courrierObject: CourrierObjectResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.courrierObject.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CourrierObjectUpdateComponent,
    resolve: {
      courrierObject: CourrierObjectResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.courrierObject.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
