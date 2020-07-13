import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Bordereau } from 'app/shared/model/bordereau.model';
import { BordereauService } from './bordereau.service';
import { BordereauComponent } from './bordereau.component';
import { BordereauDetailComponent } from './bordereau-detail.component';
import { BordereauUpdateComponent } from './bordereau-update.component';
import { BordereauDeletePopupComponent } from './bordereau-delete-dialog.component';
import { IBordereau } from 'app/shared/model/bordereau.model';

@Injectable({ providedIn: 'root' })
export class BordereauResolve implements Resolve<IBordereau> {
  constructor(private service: BordereauService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBordereau> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Bordereau>) => response.ok),
        map((bordereau: HttpResponse<Bordereau>) => bordereau.body)
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
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.bordereau.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BordereauDetailComponent,
    resolve: {
      bordereau: BordereauResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.bordereau.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BordereauUpdateComponent,
    resolve: {
      bordereau: BordereauResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.bordereau.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BordereauUpdateComponent,
    resolve: {
      bordereau: BordereauResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.bordereau.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const bordereauPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: BordereauDeletePopupComponent,
    resolve: {
      bordereau: BordereauResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.bordereau.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
