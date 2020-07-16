import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OrmvahTestModule } from '../../../test.module';
import { CourrierObjectDetailComponent } from 'app/entities/courrier-object/courrier-object-detail.component';
import { CourrierObject } from 'app/shared/model/courrier-object.model';

describe('Component Tests', () => {
  describe('CourrierObject Management Detail Component', () => {
    let comp: CourrierObjectDetailComponent;
    let fixture: ComponentFixture<CourrierObjectDetailComponent>;
    const route = ({ data: of({ courrierObject: new CourrierObject(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrmvahTestModule],
        declarations: [CourrierObjectDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CourrierObjectDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CourrierObjectDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load courrierObject on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.courrierObject).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
