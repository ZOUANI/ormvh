import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILeService } from 'app/shared/model/le-service.model';
import { LeServiceService } from './le-service.service';

@Component({
  selector: 'jhi-le-service-delete-dialog',
  templateUrl: './le-service-delete-dialog.component.html'
})
export class LeServiceDeleteDialogComponent {
  leService: ILeService;

  constructor(protected leServiceService: LeServiceService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.leServiceService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'leServiceListModification',
        content: 'Deleted an leService'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-le-service-delete-popup',
  template: ''
})
export class LeServiceDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ leService }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(LeServiceDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.leService = leService;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/le-service', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/le-service', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
