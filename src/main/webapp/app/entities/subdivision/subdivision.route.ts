import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISubdivision, Subdivision } from 'app/shared/model/subdivision.model';
import { SubdivisionService } from './subdivision.service';
import { SubdivisionComponent } from './subdivision.component';
import { SubdivisionDetailComponent } from './subdivision-detail.component';
import { SubdivisionUpdateComponent } from './subdivision-update.component';

@Injectable({ providedIn: 'root' })
export class SubdivisionResolve implements Resolve<ISubdivision> {
  constructor(private service: SubdivisionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISubdivision> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((subdivision: HttpResponse<Subdivision>) => {
          if (subdivision.body) {
            return of(subdivision.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Subdivision());
  }
}

export const subdivisionRoute: Routes = [
  {
    path: '',
    component: SubdivisionComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.subdivision.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SubdivisionDetailComponent,
    resolve: {
      subdivision: SubdivisionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.subdivision.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SubdivisionUpdateComponent,
    resolve: {
      subdivision: SubdivisionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.subdivision.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SubdivisionUpdateComponent,
    resolve: {
      subdivision: SubdivisionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.subdivision.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
