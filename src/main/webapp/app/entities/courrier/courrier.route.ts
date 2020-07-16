import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICourrier, Courrier } from 'app/shared/model/courrier.model';
import { CourrierService } from './courrier.service';
import { CourrierComponent } from './courrier.component';
import { CourrierDetailComponent } from './courrier-detail.component';
import { CourrierUpdateComponent } from './courrier-update.component';

@Injectable({ providedIn: 'root' })
export class CourrierResolve implements Resolve<ICourrier> {
  constructor(private service: CourrierService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICourrier> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((courrier: HttpResponse<Courrier>) => {
          if (courrier.body) {
            return of(courrier.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Courrier());
  }
}

export const courrierRoute: Routes = [
  {
    path: '',
    component: CourrierComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'ormvahApp.courrier.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CourrierDetailComponent,
    resolve: {
      courrier: CourrierResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.courrier.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CourrierUpdateComponent,
    resolve: {
      courrier: CourrierResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.courrier.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CourrierUpdateComponent,
    resolve: {
      courrier: CourrierResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.courrier.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
