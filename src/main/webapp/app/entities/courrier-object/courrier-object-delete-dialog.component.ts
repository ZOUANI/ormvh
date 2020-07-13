import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICourrierObject } from 'app/shared/model/courrier-object.model';
import { CourrierObjectService } from './courrier-object.service';

@Component({
  selector: 'jhi-courrier-object-delete-dialog',
  templateUrl: './courrier-object-delete-dialog.component.html'
})
export class CourrierObjectDeleteDialogComponent {
  courrierObject: ICourrierObject;

  constructor(
    protected courrierObjectService: CourrierObjectService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.courrierObjectService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'courrierObjectListModification',
        content: 'Deleted an courrierObject'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-courrier-object-delete-popup',
  template: ''
})
export class CourrierObjectDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ courrierObject }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CourrierObjectDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.courrierObject = courrierObject;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/courrier-object', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/courrier-object', { outlets: { popup: null } }]);
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
