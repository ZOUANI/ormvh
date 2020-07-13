import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Voie } from 'app/shared/model/voie.model';
import { VoieService } from './voie.service';
import { VoieComponent } from './voie.component';
import { VoieDetailComponent } from './voie-detail.component';
import { VoieUpdateComponent } from './voie-update.component';
import { VoieDeletePopupComponent } from './voie-delete-dialog.component';
import { IVoie } from 'app/shared/model/voie.model';

@Injectable({ providedIn: 'root' })
export class VoieResolve implements Resolve<IVoie> {
  constructor(private service: VoieService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IVoie> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Voie>) => response.ok),
        map((voie: HttpResponse<Voie>) => voie.body)
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
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.voie.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: VoieDetailComponent,
    resolve: {
      voie: VoieResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.voie.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: VoieUpdateComponent,
    resolve: {
      voie: VoieResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.voie.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: VoieUpdateComponent,
    resolve: {
      voie: VoieResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.voie.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const voiePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: VoieDeletePopupComponent,
    resolve: {
      voie: VoieResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.voie.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
