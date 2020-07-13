import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Evaluation } from 'app/shared/model/evaluation.model';
import { EvaluationService } from './evaluation.service';
import { EvaluationComponent } from './evaluation.component';
import { EvaluationDetailComponent } from './evaluation-detail.component';
import { EvaluationUpdateComponent } from './evaluation-update.component';
import { EvaluationDeletePopupComponent } from './evaluation-delete-dialog.component';
import { IEvaluation } from 'app/shared/model/evaluation.model';

@Injectable({ providedIn: 'root' })
export class EvaluationResolve implements Resolve<IEvaluation> {
  constructor(private service: EvaluationService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEvaluation> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Evaluation>) => response.ok),
        map((evaluation: HttpResponse<Evaluation>) => evaluation.body)
      );
    }
    return of(new Evaluation());
  }
}

export const evaluationRoute: Routes = [
  {
    path: '',
    component: EvaluationComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.evaluation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EvaluationDetailComponent,
    resolve: {
      evaluation: EvaluationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.evaluation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EvaluationUpdateComponent,
    resolve: {
      evaluation: EvaluationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.evaluation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EvaluationUpdateComponent,
    resolve: {
      evaluation: EvaluationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.evaluation.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const evaluationPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EvaluationDeletePopupComponent,
    resolve: {
      evaluation: EvaluationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ormvahApp.evaluation.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
