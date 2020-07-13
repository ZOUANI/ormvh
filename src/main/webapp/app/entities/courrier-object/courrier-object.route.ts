import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CourrierObject } from 'app/shared/model/courrier-object.model';
import { CourrierObjectService } from './courrier-object.service';
import { CourrierObjectComponent } from './courrier-object.component';
import { CourrierObjectDetailComponent } from './courrier-object-detail.component';
import { CourrierObjectUpdateComponent } from './courrier-object-update.component';
import { CourrierObjectDeletePopupComponent } from './courrier-object-delete-dialog.component';
import { ICourrierObject } from 'app/shared/model/courrier-object.model';

@Injectable({ providedIn: 'root' })
export class CourrierObjectResolve implements Resolve<ICourrierObject> {
  constructor(private service: CourrierObjectService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICourrierObject> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CourrierObject>) => response.ok),
        map((courrierObject: HttpResponse<CourrierObject>) => courrierObject.body)
      );
    }
    return of(new CourrierObject());
  }
}

export const courrierObjectRoute: Routes = [
  {
    path: '',
    component: CourrierObjectComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.courrierObject.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CourrierObjectDetailComponent,
    resolve: {
      courrierObject: CourrierObjectResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.courrierObject.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CourrierObjectUpdateComponent,
    resolve: {
      courrierObject: CourrierObjectResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.courrierObject.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CourrierObjectUpdateComponent,
    resolve: {
      courrierObject: CourrierObjectResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.courrierObject.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const courrierObjectPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CourrierObjectDeletePopupComponent,
    resolve: {
      courrierObject: CourrierObjectResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.courrierObject.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
