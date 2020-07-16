import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IExpeditor, Expeditor } from 'app/shared/model/expeditor.model';
import { ExpeditorService } from './expeditor.service';
import { ExpeditorComponent } from './expeditor.component';
import { ExpeditorDetailComponent } from './expeditor-detail.component';
import { ExpeditorUpdateComponent } from './expeditor-update.component';

@Injectable({ providedIn: 'root' })
export class ExpeditorResolve implements Resolve<IExpeditor> {
  constructor(private service: ExpeditorService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IExpeditor> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((expeditor: HttpResponse<Expeditor>) => {
          if (expeditor.body) {
            return of(expeditor.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Expeditor());
  }
}

export const expeditorRoute: Routes = [
  {
    path: '',
    component: ExpeditorComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'ormvahApp.expeditor.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ExpeditorDetailComponent,
    resolve: {
      expeditor: ExpeditorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.expeditor.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ExpeditorUpdateComponent,
    resolve: {
      expeditor: ExpeditorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.expeditor.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ExpeditorUpdateComponent,
    resolve: {
      expeditor: ExpeditorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.expeditor.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
