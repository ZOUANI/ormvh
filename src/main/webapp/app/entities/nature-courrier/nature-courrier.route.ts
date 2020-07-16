import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { INatureCourrier, NatureCourrier } from 'app/shared/model/nature-courrier.model';
import { NatureCourrierService } from './nature-courrier.service';
import { NatureCourrierComponent } from './nature-courrier.component';
import { NatureCourrierDetailComponent } from './nature-courrier-detail.component';
import { NatureCourrierUpdateComponent } from './nature-courrier-update.component';

@Injectable({ providedIn: 'root' })
export class NatureCourrierResolve implements Resolve<INatureCourrier> {
  constructor(private service: NatureCourrierService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INatureCourrier> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((natureCourrier: HttpResponse<NatureCourrier>) => {
          if (natureCourrier.body) {
            return of(natureCourrier.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new NatureCourrier());
  }
}

export const natureCourrierRoute: Routes = [
  {
    path: '',
    component: NatureCourrierComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.natureCourrier.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NatureCourrierDetailComponent,
    resolve: {
      natureCourrier: NatureCourrierResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.natureCourrier.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NatureCourrierUpdateComponent,
    resolve: {
      natureCourrier: NatureCourrierResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.natureCourrier.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NatureCourrierUpdateComponent,
    resolve: {
      natureCourrier: NatureCourrierResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.natureCourrier.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
