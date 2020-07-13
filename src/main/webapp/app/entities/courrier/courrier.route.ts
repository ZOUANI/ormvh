import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Courrier } from 'app/shared/model/courrier.model';
import { CourrierService } from './courrier.service';
import { CourrierComponent } from './courrier.component';
import { CourrierDetailComponent } from './courrier-detail.component';
import { CourrierUpdateComponent } from './courrier-update.component';
import { CourrierDeletePopupComponent } from './courrier-delete-dialog.component';
import { ICourrier } from 'app/shared/model/courrier.model';

@Injectable({ providedIn: 'root' })
export class CourrierResolve implements Resolve<ICourrier> {
  constructor(private service: CourrierService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICourrier> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Courrier>) => response.ok),
        map((courrier: HttpResponse<Courrier>) => courrier.body)
      );
    }
    return of(new Courrier());
  }
}

export const courrierRoute: Routes = [
  {
    path: '',
    component: CourrierComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ormvahApp.courrier.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CourrierDetailComponent,
    resolve: {
      courrier: CourrierResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.courrier.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CourrierUpdateComponent,
    resolve: {
      courrier: CourrierResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.courrier.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CourrierUpdateComponent,
    resolve: {
      courrier: CourrierResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.courrier.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const courrierPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CourrierDeletePopupComponent,
    resolve: {
      courrier: CourrierResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.courrier.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
