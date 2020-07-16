import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBordereau, Bordereau } from 'app/shared/model/bordereau.model';
import { BordereauService } from './bordereau.service';
import { BordereauComponent } from './bordereau.component';
import { BordereauDetailComponent } from './bordereau-detail.component';
import { BordereauUpdateComponent } from './bordereau-update.component';

@Injectable({ providedIn: 'root' })
export class BordereauResolve implements Resolve<IBordereau> {
  constructor(private service: BordereauService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBordereau> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((bordereau: HttpResponse<Bordereau>) => {
          if (bordereau.body) {
            return of(bordereau.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Bordereau());
  }
}

export const bordereauRoute: Routes = [
  {
    path: '',
    component: BordereauComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.bordereau.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BordereauDetailComponent,
    resolve: {
      bordereau: BordereauResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.bordereau.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BordereauUpdateComponent,
    resolve: {
      bordereau: BordereauResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.bordereau.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BordereauUpdateComponent,
    resolve: {
      bordereau: BordereauResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.bordereau.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
