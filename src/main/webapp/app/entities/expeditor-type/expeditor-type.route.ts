import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IExpeditorType, ExpeditorType } from 'app/shared/model/expeditor-type.model';
import { ExpeditorTypeService } from './expeditor-type.service';
import { ExpeditorTypeComponent } from './expeditor-type.component';
import { ExpeditorTypeDetailComponent } from './expeditor-type-detail.component';
import { ExpeditorTypeUpdateComponent } from './expeditor-type-update.component';

@Injectable({ providedIn: 'root' })
export class ExpeditorTypeResolve implements Resolve<IExpeditorType> {
  constructor(private service: ExpeditorTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IExpeditorType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((expeditorType: HttpResponse<ExpeditorType>) => {
          if (expeditorType.body) {
            return of(expeditorType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
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
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.expeditorType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ExpeditorTypeDetailComponent,
    resolve: {
      expeditorType: ExpeditorTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.expeditorType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ExpeditorTypeUpdateComponent,
    resolve: {
      expeditorType: ExpeditorTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.expeditorType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ExpeditorTypeUpdateComponent,
    resolve: {
      expeditorType: ExpeditorTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.expeditorType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
