import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IVoie, Voie } from 'app/shared/model/voie.model';
import { VoieService } from './voie.service';
import { VoieComponent } from './voie.component';
import { VoieDetailComponent } from './voie-detail.component';
import { VoieUpdateComponent } from './voie-update.component';

@Injectable({ providedIn: 'root' })
export class VoieResolve implements Resolve<IVoie> {
  constructor(private service: VoieService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVoie> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((voie: HttpResponse<Voie>) => {
          if (voie.body) {
            return of(voie.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Voie());
  }
}

export const voieRoute: Routes = [
  {
    path: '',
    component: VoieComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.voie.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VoieDetailComponent,
    resolve: {
      voie: VoieResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.voie.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VoieUpdateComponent,
    resolve: {
      voie: VoieResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.voie.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VoieUpdateComponent,
    resolve: {
      voie: VoieResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ormvahApp.voie.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
