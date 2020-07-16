import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OrmvahTestModule } from '../../../test.module';
import { CourrierObjectComponent } from 'app/entities/courrier-object/courrier-object.component';
import { CourrierObjectService } from 'app/entities/courrier-object/courrier-object.service';
import { CourrierObject } from 'app/shared/model/courrier-object.model';

describe('Component Tests', () => {
  describe('CourrierObject Management Component', () => {
    let comp: CourrierObjectComponent;
    let fixture: ComponentFixture<CourrierObjectComponent>;
    let service: CourrierObjectService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrmvahTestModule],
        declarations: [CourrierObjectComponent],
      })
        .overrideTemplate(CourrierObjectComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CourrierObjectComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CourrierObjectService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CourrierObject(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.courrierObjects && comp.courrierObjects[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
